package sadek.doctorAppointments.doctorAvailability.unitTest;

import org.junit.jupiter.api.Test;
import sadek.doctorAppointments.doctorAvailability.internal.business.exceptions.InvalidSlotCost;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.SlotCost;

import static org.junit.jupiter.api.Assertions.*;

public class SlotCostTest {
    @Test
    void constructor_validSlotCost_shouldCreateSlotCost() {
        double validCost = 50.0;

        SlotCost slotCost = new SlotCost(validCost);

        assertNotNull(slotCost);
        assertEquals(validCost, slotCost.value());
    }

    @Test
    void constructor_zeroSlotCost_shouldThrowInvalidSlotCost() {
        double zeroCost = 0.0;

        assertThrows(InvalidSlotCost.class, () -> new SlotCost(zeroCost));
    }

    @Test
    void constructor_negativeSlotCost_shouldThrowInvalidSlotCost() {
        double negativeCost = -10.0;

        assertThrows(InvalidSlotCost.class, () -> new SlotCost(negativeCost));
    }

    @Test
    void value_getValue_shouldReturnCorrectCost() {
        double costValue = 100.0;

        SlotCost slotCost = new SlotCost(costValue);

        assertEquals(costValue, slotCost.value());
    }
}
