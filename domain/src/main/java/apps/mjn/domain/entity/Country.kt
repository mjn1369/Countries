package apps.mjn.domain.entity

data class Country(
    val name: String,
    val alpha2Code: String,
    val capital: String?,
    val region: String,
    val population: Long,
    val area: Double?,
    val callingCodes: List<String>,
    val latlng: List<Double>
) {
    override fun equals(other: Any?): Boolean {
        other as Country
        return name == other.name
    }
}