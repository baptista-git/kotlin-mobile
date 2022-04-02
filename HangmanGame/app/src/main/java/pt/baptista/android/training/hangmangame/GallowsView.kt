package pt.baptista.android.training.hangmangame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.pow
import kotlin.math.sqrt

class GallowsView(context : Context, attrs: AttributeSet) : View(context, attrs) {
    private val filledBlack = ink(Color.BLACK, Paint.Style.FILL_AND_STROKE)
    private val borderBlack = ink(Color.BLACK, Paint.Style.STROKE)
    var steps = 7
        set(st) {
            field = st; invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        (0..steps).forEach { idx -> drawSteps[idx](canvas) }
    }

    private val drawSteps = arrayOf(
        ::drawBorder,
        ::drawGallows,
        ::drawHead,
        ::drawBody,
        ::drawLeftArm,
        ::drawRightArm,
        ::drawLeftLeg,
        ::drawRightLeg
    )

    private fun drawBorder(canvas: Canvas) =
        canvas.drawRect(
            0.0F,
            0.0F,
            this.width.toFloat() - 1,
            this.height.toFloat() - 1,
            borderBlack
        )

    private fun drawGallows(canvas: Canvas) {
        canvas.drawRect(
            width.percent(25),
            height.percent(78),
            width.percent(75),
            height.percent(87),
            filledBlack
        )
        canvas.drawRect(
            width.percent(65),
            height.percent(15),
            width.percent(70),
            height.percent(78),
            filledBlack
        )
        canvas.drawRect(
            width.percent(40),
            height.percent(15),
            width.percent(65),
            height.percent(20),
            filledBlack
        )
        canvas.drawRect(
            width.percent(44),
            height.percent(20),
            width.percent(46),
            height.percent(30),
            filledBlack
        )
    }

    private fun drawHead(canvas: Canvas) {
//        canvas.save()
//        canvas.rotate(-3.0F, width.percent(45), height.percent(45))
        canvas.drawOval(width.percent(41),height.percent(30), width.percent(49),height.percent(40),borderBlack)
//        canvas.restore()
    }

    private fun drawBody(canvas: Canvas) =
        canvas.drawRoundRect(
            width.percent(40),
            height.percent(40),
            width.percent(50),
            height.percent(56),
            50.5F,
            50.5F,
            borderBlack
        )

    private fun drawLeftArm(canvas: Canvas) =
        canvas.drawRoundRect(
            width.percent(35),
            height.percent(42),
            width.percent(39),
            height.percent(58),
            50.5F,
            50.5F,
            borderBlack
        )

    private fun drawRightArm(canvas: Canvas) =
        canvas.drawRoundRect(
            width.percent(51),
            height.percent(42),
            width.percent(55),
            height.percent(58),
            50.5F,
            50.5F,
            borderBlack
        )

    private fun drawLeftLeg(canvas: Canvas) =
        canvas.drawRoundRect(
            width.percent(39),
            height.percent(56),
            width.percent(44),
            height.percent(73),
            50.5F,
            50.5F,
            borderBlack
        )

    private fun drawRightLeg(canvas: Canvas) =
        canvas.drawRoundRect(
            width.percent(46),
            height.percent(56),
            width.percent(51),
            height.percent(73),
            50.5F,
            50.5F,
            borderBlack
        )

    private fun ink(color: Int, style: Paint.Style): Paint {
        val paint = Paint()
        paint.color = color
        paint.style = style
        paint.strokeWidth = 5.0F
        return paint
    }

    private fun Int.percent(int: Int) = this.toFloat() / 100 * int
}
