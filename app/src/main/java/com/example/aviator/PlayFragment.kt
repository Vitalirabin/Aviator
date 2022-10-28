package com.example.aviator

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class PlayFragment : Fragment() {

    private lateinit var rocketView: ImageView
    private lateinit var pointsView: TextView
    private lateinit var constraintLayoutPlay: ConstraintLayout
    private lateinit var rocketAnimation: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rocketView = view.findViewById(R.id.rocket_view)
        pointsView = view.findViewById(R.id.points)
        constraintLayoutPlay = view.findViewById(R.id.constraint_layout_play)
        view.findViewById<Button>(R.id.button_play).setOnClickListener {
            startPlayAnimation()
        }
    }

    fun startPlayAnimation() {
        rocketAnimation = AnimationUtils.loadAnimation(context, R.anim.rocket_anim)
        val time = (3000..10000).random().toLong()
        val valueAnimator = ValueAnimator.ofInt("0".toInt(), time.toInt())
        valueAnimator.duration = time
        valueAnimator.addUpdateListener {
            pointsView.text = (it.getAnimatedValue().toString().toFloat() / 1000).toString()
        }
        rocketAnimation.duration = time
        rocketView.startAnimation(rocketAnimation)
        valueAnimator.start()

    }

}