package apps.mjn.domain.data

import apps.mjn.domain.entity.Country

internal class CountryGenerator {
    companion object {
        private const val alphabetLowerCase = "abcdefghijklnopqrstuvwxyz"
        private const val alphabetUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun randomCountries(size: Int): List<Country> {
            val result = mutableListOf<Country>()
            repeat(size){
                result.add(randomCountry())
            }
            return result
        }

        fun randomCountry() = Country(
            randomLowerCaseString(7),
            randomUpperCaseString(2),
            randomLowerCaseString(7),
            randomLowerCaseString(5)
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
    }
}