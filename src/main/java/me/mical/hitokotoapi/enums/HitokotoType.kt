package me.mical.hitokotoapi.enums

enum class HitokotoType constructor(private val typeName: String) {
    A("动画"),
    B("漫画"),
    C("游戏"),
    D("文学"),
    E("原创"),
    F("来自网络"),
    G("其他"),
    H("抖机灵"),
    I("诗词"),
    J("网易云"),
    K("哲学"),
    L("抖机灵");

    fun toName(): String = typeName
}