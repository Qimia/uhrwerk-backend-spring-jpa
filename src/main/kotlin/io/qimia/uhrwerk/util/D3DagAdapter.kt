package io.qimia.uhrwerk.util

import io.qimia.uhrwerk.tools.TableInfo
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge

class D3DagAdapter {

    fun buildJson(dag: DefaultDirectedGraph<TableInfo, DefaultEdge>): ArrayList<D3DagNode> {
        val vertexSet = dag.vertexSet()
        val edgeSet = dag.edgeSet()
        val nodes = ArrayList<D3DagNode>()
        for (vertex: TableInfo in vertexSet) {
            val node = D3DagNode(vertex.ref.table.toString())
            if ((vertexSet.indexOf(vertex) % 2) == 1) {
                node.processed = vertex.processed
            } else {
                node.processed = true
            } //TODO: remove this if condition when correct "processed" variable comes through
            nodes.add(node)
        }
        for (edge: DefaultEdge in edgeSet) {
            dag.getEdgeTarget(edge).ref.table.toString() //gets the first source edge ID
            dag.getEdgeSource(edge).ref.table.toString() //gets the parent ID of the source edge
            nodes.first { s ->
                s.id.equals(dag.getEdgeSource(edge).ref.table.toString())
            }.parentIds.add(dag.getEdgeTarget(edge).ref.table.toString())
        }
        return nodes
    }
}