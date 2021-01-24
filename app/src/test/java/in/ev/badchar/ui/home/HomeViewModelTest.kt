package `in`.ev.badchar.ui.home

import `in`.ev.domain.repository.BadCharacterRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.impl.annotations.MockK
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Error
import `in`.ev.domain.model.Response
import `in`.ev.domain.usecase.GetCharacterUsecase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@RunWith(JUnit4::class)
class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: GetCharacterUsecase

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    val char = Character(
        birthday = "sadsa",
        appearance = listOf(1, 2, 3),
        category = "sad",
        img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
        name = "sadsa",
        nickname = "sfsaf",
        occupation = listOf("wqe", "qwew"),
        betterCallSaulAppearance = listOf(5, 3, 2),
        charId = 5,
        portrayed = "sda",
        status = "sda"
    )
    val characters = mutableListOf<Character>(char)
    val errorResponse = Error( status_message = "service error")
    private val networkResultSuccess = Response.ApiCallSuccess<List<Character>>(characters)

    private val networkResultError = Response.ApiCallError((errorResponse))

    private val networkLoadingT = Response.Loading<Boolean>(true)
    private val networkLoadingF = Response.Loading<Boolean>(false)

    private val flowSuccess = flowOf(networkResultSuccess)
    private val flowFailure = flowOf(networkResultError)
    private val flowLoadingFalse = flowOf(networkLoadingF)
    private val flowLoadingTrue = flowOf(networkLoadingT)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun Given_Repository_Get_Char_When_Get_Bad_Char_Then_Api_Success() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
         coEvery {
                useCase.execute()
            } returns flowSuccess
            viewModel = HomeViewModel(useCase)
            flowSuccess.collect {  data ->
                data.data?.isNotEmpty()?.let { assert(it) }
            }
        }

    @Test
    fun Given_Repository_Get_Char_When_Get_Bad_Char_Then_Api_Error() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery {
                useCase.execute()
            } returns flowFailure
            viewModel = HomeViewModel(useCase)
            flowFailure.collect {  data ->
                data.error?.let {
                    assert(it.status_message.equals("service error"))
                }
            }
        }

    @Test
    fun given_Repository_Get_Emit_True_When_Get_Bad_Char_Then_True() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery {
                useCase.execute()
            } returns flowSuccess
            viewModel = HomeViewModel(useCase)
            flowSuccess.map {
                flowLoadingTrue
            }
            flowLoadingTrue.collect {  data ->
                assert(data.loading)
            }
        }

    @Test
    fun given_Repository_Get_Emit_False_When_Get_Bad_Char_Then_False() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            coEvery {
                useCase.execute()
            } returns flowSuccess
            viewModel = HomeViewModel(useCase)
            flowSuccess.map {
                flowLoadingFalse
            }
            flowLoadingTrue.collect {  data ->
                assert(data.loading)
            }
        }


}