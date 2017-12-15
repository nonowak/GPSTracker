package no.nowak.core.infrastructure.profileRegex

import org.springframework.context.annotation.Condition
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.context.annotation.ConditionContext
import java.util.regex.Pattern


internal class ProfileRegexCondition : Condition {

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        val environment = context.environment
        if (environment != null) {
            val profiles = environment.activeProfiles
            val attrs = metadata.getAllAnnotationAttributes(ProfileRegex::class.java.name)
            if (attrs != null) {
                for (value in attrs["value"]!!) {
                    for (profile in profiles) {
                        val matches = Pattern.matches(value as String, profile)
                        if (matches) {
                            return true
                        }
                    }
                }
                return false
            }
        }
        return true
    }
}