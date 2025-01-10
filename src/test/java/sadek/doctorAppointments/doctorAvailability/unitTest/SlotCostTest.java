package sadek.doctorAppointments.doctorAvailability.unitTest;

import org.junit.jupiter.api.Test;
import sadek.doctorAppointments.shared.domain.exceptions.InvalidCostException;
import sadek.doctorAppointments.shared.domain.valueObject.Cost;

import static org.junit.jupiter.api.Assertions.*;

public class SlotCostTest {
    @Test
    void constructor_validSlotCost_shouldCreateSlotCost() {
        double validCost = 50.0;

        Cost slotCost = new Cost(validCost);

        assertNotNull(slotCost);
        assertEquals(validCost, slotCost.value());
    }

    @Test
    void constructor_zeroSlotCost_shouldThrowInvalidSlotCost() {
        double zeroCost = 0.0;

        assertThrows(InvalidCostException.class, () -> new Cost(zeroCost));
    }

    @Test
    void constructor_negativeSlotCost_shouldThrowInvalidSlotCost() {
        double negativeCost = -10.0;

        assertThrows(InvalidCostException.class, () -> new Cost(negativeCost));
    }

    @Test
    void value_getValue_shouldReturnCorrectCost() {
        double costValue = 100.0;

        Cost slotCost = new Cost(costValue);

        assertEquals(costValue, slotCost.value());
    }
}
