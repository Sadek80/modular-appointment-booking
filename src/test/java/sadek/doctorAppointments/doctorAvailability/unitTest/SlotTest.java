package sadek.doctorAppointments.doctorAvailability.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sadek.doctorAppointments.doctorAvailability.internal.business.events.SlotUpdatedEvent;
import sadek.doctorAppointments.shared.domain.exceptions.InvalidCostException;
import sadek.doctorAppointments.shared.domain.exceptions.InvalidTimeRangeException;
import sadek.doctorAppointments.doctorAvailability.internal.business.exceptions.SlotRuleViolation;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.doctor.DoctorId;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.Slot;
import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    private DoctorId doctorId;
    private LocalDateTime now;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        doctorId = new DoctorId(UUID.randomUUID());
        now = LocalDateTime.of(2025, 1, 3, 0, 0);
        startTime = now.plusDays(1).withHour(10).withMinute(0); // Tomorrow at 10:00 AM
        endTime = startTime.plusHours(2); // 2 hours duration
    }

    @Test
    void create_validSlotData_shouldBeCreatedSuccessfully() {
        double cost = 50.0;

        Slot slot = Slot.create(doctorId, startTime, endTime, cost, now);

        assertNotNull(slot);
        assertEquals(doctorId, slot.getDoctorId());
        assertEquals(startTime, slot.getTimeRange().startTime());
        assertEquals(endTime, slot.getTimeRange().endTime());
        assertEquals(cost, slot.getCost().value());
        assertFalse(slot.isReserved());
    }
    @Test
    void create_moreThanTwoHoursDuration_shouldThrowInvalidSlotTimeRange() {
        LocalDateTime invalidEndTime = startTime.plusHours(3);

        assertThrows(SlotRuleViolation.class, () -> {
            Slot.create(doctorId, startTime, invalidEndTime, 50.0, now);
        });
    }

    @Test
    void create_slotTimeInThePast_shouldThrowInvalidSlotTimeRange() {
        LocalDateTime pastStartTime = now.minusDays(1);

        assertThrows(InvalidTimeRangeException.class, () -> {
            Slot.create(doctorId, pastStartTime, pastStartTime.plusHours(1), 50.0, now);
        });
    }

    @Test
    void create_invalidCost_shouldThrowInvalidSlotCost() {
        double invalidCost = 0.0;

        assertThrows(InvalidCostException.class, () -> {
            Slot.create(doctorId, startTime, endTime, invalidCost, now);
        });
    }

    @Test
    void update_validSlotData_shouldBeUpdatedSuccessfully() {
        double newCost = 60.0;
        LocalDateTime newStartTime = startTime.plusHours(1);
        LocalDateTime newEndTime = newStartTime.plusHours(2);

        Slot slot = Slot.create(doctorId, startTime, endTime, 50.0, now);
        slot.update(newStartTime, newEndTime, newCost, now);

        assertEquals(newStartTime, slot.getTimeRange().startTime());
        assertEquals(newEndTime, slot.getTimeRange().endTime());
        assertEquals(newCost, slot.getCost().value());
    }

    @Test
    void update_currentTimeLessThanTwoHoursBeforeReservedSlotStart_shouldThrowSlotUpdateViolation() {
        LocalDateTime twoHoursBeforeStart = startTime.minusHours(1);

        Slot slot = Slot.create(doctorId, startTime, endTime, 50.0, now);
        slot.reserve(now);

        assertThrows(SlotRuleViolation.class, () -> {
            slot.update(startTime.plusHours(1), startTime.plusHours(3), 60.0, twoHoursBeforeStart);
        });
    }

    @Test
    void update_timeRangeIsInDifferentDayOfTheReservedSlot_shouldThrowInvalidSlotTimeRange() {
        LocalDateTime newStartTime = startTime.plusDays(1); // Next day
        LocalDateTime newEndTime = newStartTime.plusHours(2);

        Slot slot = Slot.create(doctorId, startTime, endTime, 50.0, now);
        slot.reserve(now);

        assertThrows(SlotRuleViolation.class, () -> {
            slot.update(newStartTime, newEndTime, 60.0, now);
        });
    }

    @Test
    void validateNoOverlapWith_overlapWithExistingSlots_shouldThrowInvalidSlotTimeRange() {
        Slot existingSlot = Slot.create(doctorId, startTime, endTime, 50.0, now);
        Slot newSlot = Slot.create(doctorId, startTime.plusHours(1), endTime.plusHours(1), 60.0, now);

        assertThrows(SlotRuleViolation.class, () -> {
            newSlot.validateNoOverlapWith(List.of(existingSlot));
        });
    }

    @Test
    void validateNoOverlapWith_noOverlapWithExistingSlots_shouldNotThrowException() {
        Slot existingSlot = Slot.create(doctorId, startTime, endTime, 50.0, now);
        Slot newSlot = Slot.create(doctorId, endTime.plusHours(1), endTime.plusHours(3), 60.0, now);

        assertDoesNotThrow(() -> newSlot.validateNoOverlapWith(List.of(existingSlot)));
    }

    @Test
    void update_shouldRaiseSlotUpdatedEventIfReserved() {
        double newCost = 70.0;
        LocalDateTime newStartTime = startTime.plusHours(1);
        LocalDateTime newEndTime = newStartTime.plusHours(2);

        Slot slot = Slot.create(doctorId, startTime, endTime, 50.0, now);
        slot.reserve(now);

        slot.update(newStartTime, newEndTime, newCost, now);

        List<IDomainEvent> domainEvents = slot.occurredEvents();
        assertInstanceOf(SlotUpdatedEvent.class, domainEvents.get(0));

        SlotUpdatedEvent event = (SlotUpdatedEvent) domainEvents.get(0);
        assertEquals(slot.getId().value(), event.slotId());
        assertEquals(newStartTime, event.startTime());
        assertEquals(newEndTime, event.endTime());
        assertEquals(newCost, event.cost());
    }
}

