package com.example.aviator

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders


class PlayFragment : Fragment() {

    private lateinit var playViewModel: PlayViewModel

    private lateinit var rocketView: ImageView
    private lateinit var pointsView: TextView
    private lateinit var constraintLayoutPlay: ConstraintLayout
    private lateinit var rocketAnimation: Animation
    private lateinit var scoreView: TextView
    private lateinit var timeView: TextView
    private lateinit var bidView: TextView
    private lateinit var bidPlusButton: ImageButton
    private lateinit var bidMinusButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        playViewModel = ViewModelProviders.of(this).get(PlayViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

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
        timeView = view.findViewById(R.id.time_textView)
        scoreView = view.findViewById(R.id.score_count)
        bidView = view.findViewById(R.id.bid_count)
        bidPlusButton = view.findViewById(R.id.button_bid_plus)
        bidMinusButton = view.findViewById(R.id.button_bid_minus)
        bidView.text = (50).toString()
        scoreView.text = playViewModel.getScore(context).toString()
        constraintLayoutPlay = view.findViewById(R.id.constraint_layout_play)
        view.findViewById<Button>(R.id.button_play).setOnClickListener {
            play()
        }
        bidPlusButton.setOnClickListener {
            addBid()
        }
        bidMinusButton.setOnClickListener {
            decreaseBid()
        }
    }

    override fun onDestroy() {
        playViewModel.setScore(context, scoreView.text.toString().toInt())
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun startPlayAnimation(): Long {
        rocketAnimation = AnimationUtils.loadAnimation(context, R.anim.rocket_anim)
        val time = (3000..10000).random().toLong()
        val valueAnimator = ValueAnimator.ofInt("0".toInt(), time.toInt())
        valueAnimator.duration = time
        valueAnimator.addUpdateListener {
            timeView.text =
                "Время: ${
                    String.format(
                        "%.2f",
                        (it.getAnimatedValue().toString().toFloat() / 1000)
                    )
                }"
            pointsView.text =
                String.format(
                    "%.2f",
                    ((it.getAnimatedValue().toString().toFloat()) / 4000)
                )
        }
        rocketAnimation.duration = time
        rocketAnimation.interpolator = AccelerateInterpolator()
        rocketView.startAnimation(rocketAnimation)
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.start()
        return time
    }

    private fun addBid() {
        var bid = bidView.text.toString().toInt()
        bid += 50
        bidView.text = bid.toString()
    }

    private fun decreaseBid() {
        var bid = bidView.text.toString().toInt()
        if (bid > 50) {
            bid -= 50
            bidView.text = bid.toString()
        } else Toast.makeText(context, "Минимальная ставка 50", Toast.LENGTH_LONG).show()
    }

    private fun play() {
        var score = scoreView.text.toString().toInt()
        val bid = bidView.text.toString().toInt()
        if (score == 0) {
            Toast.makeText(context, "У вас на балансе неосталось очков!", Toast.LENGTH_LONG).show()
        } else {
            if (score >= bid) {
                score -= bid
            } else {
                Toast.makeText(context, "У вас на балансе недостаточно очков!", Toast.LENGTH_LONG)
                    .show()
            }
        }
        val time = startPlayAnimation()
        score += ((bid * time / 4000).toInt())
        android.os.Handler().postDelayed({ scoreView.text = score.toString() }, time)
        playViewModel.setScore(context, score)
    }
}