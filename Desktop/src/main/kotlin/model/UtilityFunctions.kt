package model

fun calcLerpT(start : Float, end : Float, current : Float) : Float{
    return (current - end) / (start - end)
}