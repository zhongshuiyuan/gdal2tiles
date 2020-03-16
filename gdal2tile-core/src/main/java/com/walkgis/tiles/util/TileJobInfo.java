package com.walkgis.tiles.util;


import java.util.List;

/**
 * Plain object to hold tile job configuration for a dataset
 **/
public class TileJobInfo {
    public String srcFile = "";
    public Integer nbDataBands = 0;
    public String outputFilePath = "";
    public String tileExtension = "";
    public Integer tileSize = 0;
    public String tileDriver = null;
    public Boolean kml = false;
    public List<int[]> tminmax;
    public Integer tminz = 0;
    public Integer tmaxz = 0;
    public String inSrsWkt = "";
    public double[] outGeoTrans;
    public double ominy;
    public Boolean isEpsg4326 = false;
    public OptionObj options = null;
    public boolean excludeTransparent = false;
    public DealFun dealFun;


    public TileJobInfo(DealFun dealFun, String srcFile, Integer nbDataBands, String outputFilePath,
                       String tileExtension, String tileDriver, int tileSize,
                       boolean kml, List<int[]> tminmax, int tminz, int tmaxz,
                       String inSrsWkt, double[] outGeoTrans, double ominy,
                       boolean isEpsg4326, OptionObj options, boolean excludeTransparent) {
        this.dealFun = dealFun;
        this.srcFile = srcFile;
        this.nbDataBands = nbDataBands;
        this.outputFilePath = outputFilePath;
        this.tileExtension = tileExtension;
        this.tileSize = tileSize;
        this.tileDriver = tileDriver;
        this.kml = kml;
        this.tminmax = tminmax;
        this.tminz = tminz;
        this.tmaxz = tmaxz;
        this.inSrsWkt = inSrsWkt;
        this.outGeoTrans = outGeoTrans;
        this.ominy = ominy;
        this.isEpsg4326 = isEpsg4326;
        this.options = options;
        this.excludeTransparent = excludeTransparent;
    }
}
