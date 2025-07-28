package com.hmzii.brainquiz

data class QuestionModel(
    var question: String? = null,
    var option1: String? = null,
    var option2: String? = null,
    var option3: String? = null,
    var option4: String? = null,
    var ans: String? = null // Make 'ans' mutable to match other options
)