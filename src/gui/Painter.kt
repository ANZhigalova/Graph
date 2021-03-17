package gui

import java.awt.Dimension
import java.awt.Graphics

interface Painter {
    fun paint(g: Graphics)
    var size: Dimension
}