package com.example.flowstasksapp

import android.util.Log
import com.example.flowstasksapp.domain.RepositoryImpl
import com.example.flowstasksapp.domain.Task
import com.example.flowstasksapp.presentation.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import org.junit.Test
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import java.time.LocalDate

class MainViewModelTest {
    private val mockRepository = mockk<RepositoryImpl>() // Создаю мок репозитория
    private val task1 = Task(
        id = 1,
        title = "test1",
        description = "test1",
        date = LocalDate.of(2007, 6, 6)
    )
    private val task2 = Task(
        id = 2,
        title = "test2",
        description = "test2",
        date = LocalDate.of(2007, 6, 6)
    )

    private lateinit var viewModel: MainViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun initViewModel() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(mockRepository)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.e(any(), any(), any<Throwable>()) } returns 0
    }

    @Test
    fun `open add task screen`() {
        viewModel.openAddScreen()
        assertTrue(viewModel.isOpenAddScreen.value)
    }

    @Test
    fun `close add task screen`() {
        viewModel.closeAddScreen()
        assertFalse(viewModel.isOpenAddScreen.value)
    }

    @Test
    fun `open update task screen by task`() {
        viewModel.openUpdateTaskScreen(task1)

        assertTrue(viewModel.isOpenUpdateTaskScreen.value) // Открыто ли окно редактирование
        assertEquals(task1, viewModel.selectedTask.value) // Выбрана ли нужная задача
    }

    @Test
    fun `close update task screen`() {
        viewModel.closeUpdateTaskScreen()

        assertFalse(viewModel.isOpenUpdateTaskScreen.value) // Закрыто ли окно
        assertEquals(null, viewModel.selectedTask.value)
    }

    @Test
    fun `add task without Exception`() = runTest {
        viewModel.openAddScreen()

        coEvery { mockRepository.addTask(task1) } returns 1L // Настраиваем мок на возврат 1

        viewModel.addTask(task1)

        // Ждём пока корутина завершится
        advanceUntilIdle()

        // Проверка, что add task был вызван один раз
        coVerify(exactly = 1) { mockRepository.addTask(task1) }
    }

    @Test
    fun `add task with exception`() = runTest {
        val exception = Exception("Ошибка бд")

        coEvery { mockRepository.addTask(task1) } throws exception // Настравиаем мок на выброс исключения

        viewModel.openAddScreen()
        viewModel.addTask(task1)

        advanceUntilIdle() // Пока корутина завершится

        coVerify(exactly = 1) { mockRepository.addTask(task1) }
    }

    @Test
    fun `delete task`() = runTest{
        coEvery{mockRepository.deleteTaskById(task1.id)} returns 1

        viewModel.deleteTask(task1.id)
        advanceUntilIdle()

        coVerify(exactly = 1) {  mockRepository.deleteTaskById(task1.id)}
    }

    @Test
    fun `update task`() = runTest{
        coEvery{mockRepository.updateTask(task1)} returns Unit

        viewModel.updateTask(task1)
        advanceUntilIdle() // Ждём пока все корутины завершаться

        coVerify(exactly = 1) {mockRepository.updateTask(task1)  }
    }

}