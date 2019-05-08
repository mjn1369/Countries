package apps.mjn.domain.entity

data class Country(
    val name: String,
    val alpha2Code: String,
    val capital: String,
    val region: String,
    val population: Long,
    val area: Double
)