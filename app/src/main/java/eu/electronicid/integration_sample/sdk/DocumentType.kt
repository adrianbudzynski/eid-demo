package eu.electronicid.integration_sample.sdk

data class DocumentType(
    val name: String,
    val id: Int
) {
    override fun toString(): String {
        return name
    }
}