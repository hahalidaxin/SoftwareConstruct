package otherDirectory;

import physicalObject.PhysicalObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Difference<E extends PhysicalObject> {
//    AF(*) = 有trackNumDiff个轨道比较结果的轨道差异对象Difference

    int trackIndex = 0;
    private int trackNumDiff;
    private List<TrackDifference> trackDiffList = new ArrayList<>();

    public Difference(int trackNumDiff) {
        this.trackNumDiff = trackNumDiff;
    }

    /**
     * 向系统轨道差异对象中添加两条对应比较的轨道
     * @param list1 轨道1上的所有物体
     * @param list2 轨道2上的所有物体
     */
    public void addTrackSet(List<E> list1, List<E> list2) {
        List<E> OriOrbit1Objs = new ArrayList<>(list1);
        List<E> OriOrbit2Objs = new ArrayList<>(list2);
        trackIndex++;
        int NumDiff = OriOrbit1Objs.size()-OriOrbit2Objs.size();
        Set<E> deleteObjs = new HashSet<>();
        for(E po1:OriOrbit1Objs) {
            for(E po2:OriOrbit2Objs) {
                if(po1.equalsObject(po2)) {
                    deleteObjs.add(po2);
                }
            }
        }
        for(E po:deleteObjs) {
            OriOrbit1Objs.removeIf((x)->x.equalsObject(po));
            OriOrbit2Objs.removeIf((x)->x.equalsObject(po));
        }

        trackDiffList.add(new TrackDifference(trackIndex,NumDiff,
                new HashSet<>(OriOrbit1Objs),new HashSet<>(OriOrbit2Objs)));

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("轨道数差异：%d%n",trackNumDiff));
        for(TrackDifference trackdiff:trackDiffList) {
            sb.append(trackdiff.toString()+"\n");
        }
        return sb.toString();
    }

    private class TrackDifference {
//       AF(*) = 数目差异为objNumDiff 互异轨道物体集合分别为orbit1Objs和orbit2Objs的单条轨道差异对象

        int index;
        int objNumDiff;
        Set<E> orbit1Objs,orbit2Objs;

        public TrackDifference(int index,int objNumDiff,Set<E> orbit1Objs,Set<E> orbit2Objs) {
            this.index = index;
            this.objNumDiff = objNumDiff;
            this.orbit1Objs = orbit1Objs;
            this.orbit2Objs = orbit2Objs;
        }

        /**
         * 获取一个轨道互异物体集合转化后的字符串
         * @param orbitObjs 一个轨道上的互异轨道物体集合
         * @return 返回转化后的字符串，如果集合数目为0则返回 空 如果数目只有一个则不加结合符号，
         * 如果数目>=1则添加集合符号
         */
        private String getObjectString(Set<E> orbitObjs) {
            StringBuilder sb = new StringBuilder();
            if(orbitObjs.size()==0) sb.append("无");
            else if(orbitObjs.size()==1) {
                for(E po:orbitObjs) {
                    sb.append(po.toString());
                }
            } else {
                sb.append("{");
                boolean flag = false;
                for(E po:orbitObjs) {
                    if(!flag) {
                        flag = true;
                        sb.append(po.toString());
                    } else {
                        sb.append(","+po.toString());
                    }
                }
                sb.append("}");
            }
            return sb.toString();
        }

        /**
         * 获取单条轨道的比较结果
         * @return 单条轨道比较结果 如果两个互异轨道物体都返回无 这说明两者并不存在物体差异，
         * 否则数目对应的互异轨道物体集合字符串
         */
        @Override
        public String toString() {
            String tmp = getObjectString(orbit1Objs)+"-"+getObjectString(orbit2Objs);
            if(tmp.equals("无-无"))
                return String.format("轨道%d的物体数量差异：%d ；",index,objNumDiff);
            else
                return String.format("轨道%d的物体数量差异：%d ；  物体差异：%s",index,objNumDiff,tmp);
        }
    }
}
