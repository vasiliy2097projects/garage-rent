package ru.garagerent.data

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FakeGarageRepositoryTest {
    @Test
    fun fakeWaits800Milliseconds() = runTest {
        val repo = FakeGarageRepository()
        val pending = async { repo.observe().first() }
        testScheduler.advanceTimeBy(799)
        assertNull(pending.completedOrNull())
        testScheduler.advanceTimeBy(1)
        val warm = pending.await().single { it.title == "Теплый гараж с ямой" }
        assertEquals(350, warm.pricePerHour)
        assertEquals(800L, MOCK_DELAY_MILLIS)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun <T> Deferred<T>.completedOrNull(): T? = if (isCompleted) getCompleted() else null
