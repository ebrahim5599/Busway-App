package com.sta.buswayapp.model
enum class DEPARTMENT_CODE(val label: String, val value: Int) {

    BUSWAY_PRODUCTION(ConstantNames.PRODUCTION_DEPARTMENT, 0),
    QUALITY_CONTROL(ConstantNames.QUALITY_DEPARTMENT, 1),
    DISPATCHING(ConstantNames.DISPATCH_DEPARTMENT, 2);

    companion object {
        fun getValue(name: String): Int {
            return values()
                .find { it.label.equals(name, ignoreCase = true) }
                ?.value ?: -1
        }
    }
}
