package model

fun inverseLerp(start : Float, end : Float, current : Float) : Float{
    return (current - end) / (start - end)
}