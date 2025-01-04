package sadek.doctorAppointments.doctorAvailability.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sadek.doctorAppointments.doctorAvailability.internal.business.dto.CreateSlotDto;
import sadek.doctorAppointments.doctorAvailability.internal.business.dto.SlotResponseDto;
import sadek.doctorAppointments.doctorAvailability.internal.business.helpers.DoctorContext;
import sadek.doctorAppointments.doctorAvailability.internal.business.mappers.SlotMapper;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.Slot;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.SlotErrors;
import sadek.doctorAppointments.doctorAvailability.internal.business.services.SlotService;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.DoctorEntity;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;
import sadek.doctorAppointments.doctorAvailability.internal.data.repositories.IDoctorRepository;
import sadek.doctorAppointments.doctorAvailability.internal.data.repositories.ISlotRepository;
import sadek.doctorAppointments.doctorAvailability.internal.business.events.SlotUpdatedEvent;
import sadek.doctorAppointments.shared.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlotServiceTest {

    @Mock
    private DoctorContext doctorContext;

    @Mock
    private ISlotRepository slotRepository;

    @Mock
    private IDoctorRepository doctorRepository;

    @Mock
    private SlotMapper slotMapper;

    @Mock
    private IDateTimeProvider dateTimeProvider;

    @Mock
    private IEventBus eventBus;

    @InjectMocks
    private SlotService slotService;

    private UUID doctorId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doctorId = UUID.randomUUID();
        now = LocalDateTime.now();

        when(doctorContext.getUserId()).thenReturn(doctorId);
        when(dateTimeProvider.nowDateTime()).thenReturn(now);
        when(doctorRepository.getReferenceById(doctorId)).thenReturn(mock(DoctorEntity.class)); // Mock doctor reference
    }


    @Test
    void createSlot_validRequest_shouldCreateSlot() {
        CreateSlotDto request = new CreateSlotDto(now.plusDays(1), now.plusDays(1).plusHours(2), 50.0);
        Slot newSlot = mock(Slot.class);
        SlotEntity slotEntity = mock(SlotEntity.class);

        when(slotMapper.mapToSlotEntity(any(Slot.class))).thenReturn(slotEntity);
        when(slotRepository.findAllByDoctorId(doctorId)).thenReturn(Collections.emptyList());

        Result<Response<UUID>> result = slotService.createSlot(request);

        verify(slotRepository).save(slotEntity);
        verify(slotMapper).mapToSlotEntity(any(Slot.class));
        verify(eventBus, times(0)).publish(any());

        assertTrue(result.isSuccess());
        assertEquals("New slot created", result.getValue().getMessage());
    }

    @Test
    void getAllSlots_shouldReturnAllSlots() {
        SlotEntity slotEntity = mock(SlotEntity.class);
        SlotResponseDto slotDto = mock(SlotResponseDto.class);

        when(slotRepository.findAllByDoctorIdWithDoctor(doctorId)).thenReturn(List.of(slotEntity));
        when(slotMapper.mapToSlotResponseDtoList(anyList())).thenReturn(List.of(slotDto));

        Result<Response<List<SlotResponseDto>>> result = slotService.getAllSlots();

        assertTrue(result.isSuccess());
        assertEquals(1, result.getValue().getData().size());
        assertEquals(slotDto, result.getValue().getData().get(0));
    }

    @Test
    void updateSlot_validRequest_shouldUpdateSlot() {
        UUID slotId = UUID.randomUUID();
        CreateSlotDto request = new CreateSlotDto(now.plusDays(1), now.plusDays(1).plusHours(2), 60.0);
        Slot slot = mock(Slot.class);
        SlotEntity slotEntity = mock(SlotEntity.class);
        DoctorEntity doctorEntity = mock(DoctorEntity.class);

        when(slotRepository.findSlotBySlotId(slotId)).thenReturn(Optional.of(slotEntity));
        when(slotMapper.mapToSlot(slotEntity)).thenReturn(slot);
        when(slotMapper.mapToSlotEntity(slot, slotEntity)).thenReturn(slotEntity);
        when(doctorRepository.getReferenceById(doctorId)).thenReturn(doctorEntity);

        SlotUpdatedEvent slotUpdatedEvent = new SlotUpdatedEvent(slotId, request.startTime(), request.endTime(), request.cost());
        when(slot.occurredEvents()).thenReturn(List.of(slotUpdatedEvent));

        Result<Void> result = slotService.updateSlot(slotId, request);

        verify(slot).update(request.startTime(), request.endTime(), request.cost(), now);
        verify(slotRepository).save(slotEntity);
        verify(eventBus).publish(slotUpdatedEvent);
        verify(slot).clearDomainEvents();

        assertTrue(result.isSuccess());
    }

    @Test
    void updateSlot_nonExistentSlot_shouldReturnNotFound() {
        UUID slotId = UUID.randomUUID();
        CreateSlotDto request = new CreateSlotDto(now.plusDays(1), now.plusDays(1).plusHours(2), 50.0);

        when(slotRepository.findById(slotId)).thenReturn(Optional.empty());

        Result<Void> result = slotService.updateSlot(slotId, request);

        verify(slotRepository, never()).save(any());
        verify(eventBus, never()).publish(any());
        assertFalse(result.isSuccess());
        assertEquals(SlotErrors.notFound, result.getError());
    }
}

