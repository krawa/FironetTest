package com.krawa.fironettest.utils;

import com.krawa.fironettest.model.Path;
import com.krawa.fironettest.model.Point;

import java.util.Arrays;

public class PathFinder {

    private static final int INF = 1000 * 1000 * 1000;
    private double distanceRes;

    /**Поиск кратчайшего пути по всем точкам по алгоритму “лучший-ближний”*/
    public Path findPath(Point[] points) {
        int vNum = points.length;

        double[][] graph = buildGraph(points);
        int[][] pathArr = new int[vNum][vNum];

        for (int i = 0; i < vNum; i++){
            int[] path = findPathFrom(i, graph);
            pathArr[i] = path;
        }

        int[] pathOptId = findOptimalPath(pathArr, graph);

        Point[] result = new Point[vNum];
        for (int i = 0; i < vNum; i++) {
            result[i] = points[pathOptId[i]];
        }

        Path path = new Path();
        path.setPoints(result);
        path.setDistance(distanceRes);

        return path;
    }

    /**Заполнение графа*/
    private double[][] buildGraph(Point[] points) {
        int vNum = points.length;
        double[][] graph = new double[vNum][vNum];
        for (int i = 0; i < vNum; i++) {
            for (int k = 0; k < vNum; k++) {
                double dist = 0;
                if(i != k) dist = Point.calcDistance(points[i], points[k]);
                graph[i][k] = dist;
            }
        }
        return graph;
    }

    /**Поиск пути с известной стартовой точкой*/
    private int[] findPathFrom(int start, double[][] graph) {
        int vNum = graph.length;
        int[] path = new int[vNum];
        Arrays.fill(path, -1);
        path[0] = start;
        for (int i = 1; i < vNum; i++) {
            path[i] = findShort(path[i-1], graph[path[i-1]], path);
        }
        return path;
    }

    /**Поиск ближайшей точки с учетом посещенных*/
    private int findShort(int start, double[] distArr, int[] visitedArr){
        double prevDist = INF;
        int shortest = 0;
        for (int i = 0; i < distArr.length; i++){
            double dist = distArr[i];
            if(start != i && !visited(i, visitedArr) && dist < prevDist) {
                shortest = i;
                prevDist = dist;
            }
        }
        return shortest;
    }

    private boolean visited(int point, int[] visitedArr) {
        for (int p : visitedArr) {
            if(point == p) return true;
        }
        return false;
    }

    /**Поиск самого короткого пути из всех*/
    private int[] findOptimalPath(int[][] pathArr, double[][] graph) {
        double minDist = INF;
        int optPath = 0;
        for (int i = 0; i < pathArr.length; i++) {
            int[] path = pathArr[i];
            double dist = calculateDistance(path, graph);
            if(dist < minDist){
                minDist = dist;
                optPath = i;
                distanceRes = dist;
            }
        }
        return pathArr[optPath];
    }

    /**Подсчет расстояния для выбранного пути*/
    private double calculateDistance(int[] path, double[][] graph) {
        int dist = 0;
        for (int i = 0; i < path.length - 1; i++) {
            dist += graph[path[i]][path[i+1]];
        }
        return dist;
    }
}
