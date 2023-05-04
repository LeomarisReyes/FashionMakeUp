package lr.projects.fashionmakeupapp.data.remote.network.utils

import lr.projects.fashionmakeupapp.data.remote.network.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

suspend fun <T : Any> handleApiRequest(
    block: suspend () -> Response<T>
) : NetworkResult<T> {

    val response = try {
        val response = block()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.ApiError(response.code(), response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.ApiError(e.code(), e.message())
    } catch (e: UnknownHostException) { // No Internet
        NetworkResult.ApiException(e)
    } catch (e: Throwable) {
        NetworkResult.ApiException(e)
    }

    return response
}
