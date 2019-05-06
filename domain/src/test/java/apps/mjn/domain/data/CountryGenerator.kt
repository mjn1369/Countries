package apps.mjn.domain.data

import apps.mjn.domain.entity.Country
import kotlin.random.Random

internal class CountryGenerator {
    companion object {
        private const val alphabetLowerCase = "abcdefghijklnopqrstuvwxyz"
        private const val alphabetUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun randomCountries(size: Int): List<Country> {
            val result = mutableListOf<Country>()
            repeat(size) {
                result.add(randomCountry())
            }
            return result
        }

        private fun randomCountry() = Country(
            randomLowerCaseString(7),
            randomUpperCaseString(2),
            randomLowerCaseString(7),
            randomLowerCaseString(5),
            randomLong(),
            randomDouble(),
            randomUrl()
        )

        private fun randomLowerCaseString(size: Int): String {
            var result = ""
            repeat(size) {
                result += alphabetLowerCase[Math.floor(Math.random() * alphabetLowerCase.length).toInt()]
            }
            return result
        }

        private fun randomUpperCaseString(size: Int): String {
            var result = ""
            repeat(size) {
                result += alphabetUpperCase[Math.floor(Math.random() * alphabetUpperCase.length).toInt()]
            }
            return result
        }

        private fun randomLong() = Random.nextLong()

        private fun randomDouble() = Random.nextDouble()

        private fun randomUrl() = "https://www." + randomLowerCaseString(10) + ".com"
    }
}