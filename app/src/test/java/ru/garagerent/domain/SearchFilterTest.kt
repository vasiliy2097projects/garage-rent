package ru.garagerent.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.garagerent.data.mockGarages

class SearchFilterTest {
    @Test
    fun emptyFilterShowsApprovedGarageFromTheSpec() {
        val shown = mockGarages.matching(emptySet())
        val warm = shown.single { it.title == "Теплый гараж с ямой" }
        assertEquals(350, warm.pricePerHour)
        assertTrue(shown.none { it.status != GarageStatus.APPROVED })
        assertEquals(5, shown.size)
    }

    @Test
    fun pitAndCompressorKeepOnlyGaragesThatHaveBoth() {
        val shown = mockGarages.matching(setOf(Amenity.INSPECTION_PIT, Amenity.COMPRESSOR))
        assertEquals(listOf("warm"), shown.map { it.id })
    }

    @Test
    fun everyChipTogetherLeavesTheListEmpty() {
        val shown = mockGarages.matching(Amenity.entries.toSet())
        assertTrue(shown.isEmpty())
    }
}
