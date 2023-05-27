package com.app.imotion.ui.screens.onboarding

import androidx.annotation.DrawableRes
import com.app.imotion.R

/**
 * Created by hani@fakhouri.eu on 2023-05-22.
 */
data class OnBoardingStepData(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
) {
    companion object {
        fun getStepsData(): List<OnBoardingStepData> {
            return listOf(
                OnBoardingStepData(
                    title = "What is Lorem Ipsum",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    image = R.drawable.onboarding_one,
                ),
                OnBoardingStepData(
                    title = "Why do we use it?",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    image = R.drawable.onboarding_two,
                ),
                OnBoardingStepData(
                    title = "Lorem Ipsum",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    image = R.drawable.onboarding_three,
                )
            )
        }
    }
}