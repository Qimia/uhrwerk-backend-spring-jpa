package io.qimia.uhrwerk.backend.jpa.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.qimia.uhrwerk.config.yaml.Reference
import io.qimia.uhrwerk.tools.TableInfo
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GraphController {
    @GetMapping(value = ["/visualizeGraph"])
    fun visualizeGraph(@RequestParam(name="json", required = true, defaultValue="empty") json: String, model: Model,): String {
        val dag =
            DefaultDirectedGraph<TableInfo, DefaultEdge>(DefaultEdge::class.java)

        val rootRef = Reference("area", "vertical", "root", "1.0")
        val root = TableInfo(rootRef,0)
        val dep1Ref = Reference("area", "vertical", "dep1", "1.0")
        val dep1 = TableInfo(dep1Ref,1)
        val dep2Ref = Reference("area", "vertical", "dep2", "1.0")
        val dep2= TableInfo(dep2Ref,1)

        dag.addVertex(root)
        dag.addVertex(dep1)
        dag.addVertex(dep2)
        dag.addEdge(dep1,root)
        dag.addEdge(dep2,root)


        val dep1Dep1Ref = Reference("area", "vertical", "dep1Dep1", "1.0")
        val dep1Dep1 = TableInfo(dep1Dep1Ref,2)

        val dep1Dep2Ref = Reference("area", "vertical", "dep1Dep2", "1.0")
        val dep1Dep2 = TableInfo(dep1Dep2Ref,2)

        val dep2Dep1Ref = Reference("area", "vertical", "dep2Dep1", "1.0")
        val dep2Dep1 = TableInfo(dep2Dep1Ref,2)

        dag.addVertex(dep1Dep1)
        dag.addVertex(dep1Dep2)
        dag.addVertex(dep2Dep1)
        dag.addEdge(dep1Dep1,dep1)
        dag.addEdge(dep1Dep2,dep1)
        dag.addEdge(dep2Dep1,dep2)

        var gson = Gson()
        println("Printing DAG JSON")
        println(gson.toJson(dag.edgeSet()))


        println(ObjectMapper().writeValueAsString(dag))
        model.addAttribute("json",json)
        return "visualizeGraph"
    }

    @GetMapping(value = ["/test-page"])
    fun testpage(@RequestParam(name="name", required = false, defaultValue="Sedat") name: String, model: Model): String {
        model.addAttribute("name", name)
        return "test-page"
    }
}