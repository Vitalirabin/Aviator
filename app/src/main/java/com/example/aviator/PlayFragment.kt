package com.example.aviator

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.Vibrator
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
import org.chromium.base.compat.ApiHelperForM.getSystemService


class PlayFragment : Fragment() {

    private lateinit var playViewModel: PlayViewModel

    private lateinit var soundPool: SoundPool
    private lateinit var assetManager: AssetManager
    private var streamID = 0
    private var bum = 0
    private var fly = 0

    private lateinit var rocketView: ImageView
    private lateinit var pointsView: TextView
    private lateinit var constraintLayoutPlay: ConstraintLayout
    private lateinit var rocketAnimation: Animation
    private lateinit var bumAnimation: Animation
    private lateinit var scoreView: TextView
    private lateinit var timeView: TextView
    private lateinit var bidView: TextView
    private lateinit var bidPlusButton: ImageButton
    private lateinit var bidMinusButton: ImageButton
    private lateinit var bumView: ImageView
    private lateinit var buttonPlay: Button

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
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()
        assetManager = requireActivity().assets
        bum = soundPool.load(assetManager.openFd("explosion.ogg"), 1)
        fly = soundPool.load(assetManager.openFd("rocket_flight_sound.ogg"), 1);
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
        bumView = view.findViewById(R.id.bum_view)
        buttonPlay = view.findViewById(R.id.button_play)
        bidView.text = (50).toString()
        scoreView.text = playViewModel.getScore(context).toString()
        constraintLayoutPlay = view.findViewById(R.id.constraint_layout_play)
        buttonPlay.setOnClickListener {
            buttonPlay.isClickable = false
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
        bumAnimation = AnimationUtils.loadAnimation(context, R.anim.bum_anim)
        bumAnimation.duration = 1000
        rocketAnimation = AnimationUtils.loadAnimation(context, R.anim.rocket_anim)
        val time = (3000..10000).random().toLong()
        val valueAnimator = ValueAnimator.ofInt("0".toInt(), time.toInt())
        valueAnimator.duration = time
        valueAnimator.addUpdateListener {
            timeView.text =
                "Time: ${
                    String.format(
                        "%.2f",
                        (it.getAnimatedValue().toString().toFloat() / 1000)
                    )
                }"
            pointsView.text =
                String.format(
                    "%.2f",
                    ((it.getAnimatedValue().toString().toFloat()) / 6000)
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
        playSound(fly)
        score += ((bid * time / 6000).toInt())
        android.os.Handler().postDelayed({
            soundPool.stop(fly)
            scoreView.text = score.toString()
            playSound(bum)
            rocketView.visibility = View.INVISIBLE
            bumView.visibility = View.VISIBLE
            bumView.bottom = rocketView.bottom
            bumView.top = rocketView.top
            if (playViewModel.getVibro(requireContext())) {
                val vibro = getSystemService(requireContext(), Vibrator::class.java) as Vibrator
                vibro.vibrate(1000)
            }
            bumView.startAnimation(bumAnimation)
        }, time)
        android.os.Handler().postDelayed({
            rocketView.visibility = View.VISIBLE
            bumView.visibility = View.INVISIBLE
            val choseFragment = ChoseFragment()
            choseFragment.show(requireActivity().supportFragmentManager, "choseFragment")
            buttonPlay.isClickable = true
        }, time + 1000)
        playViewModel.setScore(context, score)
    }


    override fun onPause() {
        super.onPause()
        soundPool.release()
    }

    private fun playSound(sound: Int): Int {
        if (playViewModel.getMute(context) ?: true) {
            if (sound > 0) {
                streamID = soundPool.play(sound, 1F, 1F, 1, 0, 1F)
            }
            return streamID
        }
        return 0
    }
}