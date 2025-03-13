package service

import com.squareup.square.SquareClient
import com.squareup.square.api.LocationsApi
import com.squareup.square.exceptions.ApiException

class SquareService(private val client: SquareClient) {

    fun fetchLocations() {
        val locationsApi: LocationsApi = client.locationsApi
        locationsApi.listLocationsAsync().thenAccept { result ->
            println("Location(s) for this account:")
            for (l in result.locations) {
                System.out.printf(
                    "%s: %s, %s, %s\n",
                    l.id, l.name,
                    l.address.addressLine1,
                    l.address.locality
                )
            }

        }.exceptionally {
            handleException(it)
            null
        }.join()

        shutdown()

    }

    private fun handleException(exception: Throwable) {
        when (val cause = exception.cause) {
            is ApiException -> cause.errors.forEach {
                println("Error: ${it.category} - ${it.code}: ${it.detail}")
            }

            else -> exception.printStackTrace()
        }
    }

    fun shutdown() {
        SquareClient.shutdown()
    }
}
