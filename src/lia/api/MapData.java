package lia.api;

public class MapData {
    public long uid;
    public MessageType type;
    public float width;
    public float height;
    public ObstacleData[] obstacles;
    public UnitLocation[] unitLocations;

    public MapData(long uid, MessageType type, float width, float height,
                   ObstacleData[] obstacles, UnitLocation[] unitLocations) {
        this.uid = uid;
        this.type = type;
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
        this.unitLocations = unitLocations;
    }
}
