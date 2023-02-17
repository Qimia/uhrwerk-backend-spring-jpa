package io.qimia.uhrwerk.util;

class D3DagNode(var id: String) {
    var parentIds: ArrayList<String> = ArrayList()
    var processed: Boolean = false;

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as D3DagNode

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
