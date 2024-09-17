package items


// ItemCategory.kt
enum class ItemCategory(val displayName: String) {
    FRAGILE("Хрупкое"),
    PERISHABLE("Скоропортящийся"),
    CHILLED("Охлажденный"),
    BULK("Сыпучий")
}