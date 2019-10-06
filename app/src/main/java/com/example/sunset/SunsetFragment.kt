package com.example.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class SunsetFragment private constructor() : Fragment() {
    private lateinit var sceneView: View
    private lateinit var sunView: View
    private lateinit var skyView: View
    private var blueSkyColor = 0
    private var sunsetSkyColor = 0
    private var nightSkyColor = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sunset, container, false)
        sceneView = view
        sunView = view.findViewById(R.id.sun)
        skyView = view.findViewById(R.id.sky)

        blueSkyColor = ContextCompat.getColor(context!!, R.color.blue_sky)
        sunsetSkyColor = ContextCompat.getColor(context!!, R.color.sunset_sky)
        nightSkyColor = ContextCompat.getColor(context!!, R.color.night_sky)

        sceneView.setOnClickListener { startAnimation() }
        return view
    }

    private fun startAnimation() {
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()
        val heightAnimator = ObjectAnimator
            .ofFloat(sunView, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        AnimatorSet().apply {
            play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator)
            start()
        }
    }

    companion object {
        fun newInstance() = SunsetFragment()
    }
}
