package com.ly.genjidialog.other

enum class DialogInitMode {
    NORMAL,//普通模式，在onCreateView()方法中加载convertView()方法
    DRAW_COMPLETE,//绘制完成模式，将在图形绘制完成后加载convertView()方法，此时可以获取到view的宽高
    LAZY//懒加载模式，自定义懒加载时长，用于有特殊需求的场景
}