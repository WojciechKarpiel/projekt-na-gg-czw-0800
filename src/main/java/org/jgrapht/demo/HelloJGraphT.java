/*
 * (C) Copyright 2003-2018, by Barak Naveh and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * See the CONTRIBUTORS.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the
 * GNU Lesser General Public License v2.1 or later
 * which is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR LGPL-2.1-or-later
 */
package org.jgrapht.demo;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;


/**
 * A simple introduction to using JGraphT.
 *
 * @author Barak Naveh
 */
public final class HelloJGraphT {
    private HelloJGraphT() {
    } // ensure non-instantiability.

    /**
     * The starting point for the demo.
     *
     * @param args ignored.
     * @throws MalformedURLException if invalid URL is constructed.
     * @throws ExportException       if graph cannot be exported.
     */
    public static void main(String[] args)
            throws MalformedURLException,
            ExportException {
        Graph<String, DefaultEdge> stringGraph = createStringGraph();

        // note undirected edges are printed as: {<v1>,<v2>}
        System.out.println("-- toString output");
        System.out.println(stringGraph.toString());
        System.out.println();


        // create a graph based on URL objects
        Graph<URL, DefaultEdge> hrefGraph = createHrefGraph();

        // find the vertex corresponding to www.jgrapht.org
        URL start = hrefGraph
                .vertexSet().stream().filter(url -> url.getHost().equals("www.jgrapht.org")).findAny()
                .get();


        // perform a graph traversal starting from that vertex
        System.out.println("-- traverseHrefGraph output");
        traverseHrefGraph(hrefGraph, start);
        System.out.println();

        System.out.println("-- renderHrefGraph output");
        renderHrefGraph(hrefGraph);
        System.out.println();
    }

    /**
     * Creates a toy directed graph based on URL objects that represents link structure.
     *
     * @return a graph based on URL objects.
     */
    private static Graph<URL, DefaultEdge> createHrefGraph()
            throws MalformedURLException {

        Graph<URL, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        URL google = new URL("http://www.google.com");
        URL wikipedia = new URL("http://www.wikipedia.org");
        URL jgrapht = new URL("http://www.jgrapht.org");

        // add the vertices
        g.addVertex(google);
        g.addVertex(wikipedia);
        g.addVertex(jgrapht);

        // add edges to create linking structure
        g.addEdge(jgrapht, wikipedia);
        g.addEdge(google, jgrapht);
        g.addEdge(google, wikipedia);
        g.addEdge(wikipedia, google);


        return g;
    }

    /**
     * Traverse a graph in depth-first order and print the vertices.
     *
     * @param hrefGraph a graph based on URL objects
     * @param start     the vertex where the traversal should start
     */
    private static void traverseHrefGraph(Graph<URL, DefaultEdge> hrefGraph, URL start) {
        Iterator<URL> iterator = new DepthFirstIterator<>(hrefGraph, start);
        while (iterator.hasNext()) {
            URL url = iterator.next();
            System.out.println(url);
        }
    }

    /**
     * Render a graph in DOT format.
     *
     * @param hrefGraph a graph based on URL objects
     */
    private static void renderHrefGraph(Graph<URL, DefaultEdge> hrefGraph)
            throws ExportException {

        // use helper classes to define how vertices should be rendered,
        // adhering to the DOT language restrictions
        ComponentNameProvider<URL> vertexIdProvider = new ComponentNameProvider<URL>() {
            public String getName(URL url) {
                return url.getHost().replace('.', '_');
            }
        };
        ComponentNameProvider<URL> vertexLabelProvider = new ComponentNameProvider<URL>() {
            public String getName(URL url) {
                return url.toString();
            }
        };
        GraphExporter<URL, DefaultEdge> exporter =
                new DOTExporter<>(vertexIdProvider, vertexLabelProvider, null);
        Writer writer = new StringWriter();
        exporter.exportGraph(hrefGraph, writer);
        System.out.println(writer.toString());
    }

    /**
     * Create a toy graph based on String objects.
     *
     * @return a graph based on String objects.
     */
    private static Graph<String, DefaultEdge> createStringGraph() {
        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        // add edges to create a circuit
        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v4);
        g.addEdge(v4, v1);

        return g;
    }
}
