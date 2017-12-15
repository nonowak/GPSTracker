package no.nowak.gpstracker.core.infrastructure.profileRegex

import org.springframework.context.annotation.Conditional
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION


@Retention(AnnotationRetention.RUNTIME)
@Target(CLASS, FUNCTION)
@Conditional(ProfileRegexCondition::class)
annotation class ProfileRegex(
        /**
         * The profile regex for which the annotated component should be registered.
         */
        val value: String)