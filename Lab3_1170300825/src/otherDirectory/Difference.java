package otherDirectory;

import physicalObject.PhysicalObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Difference<E extends PhysicalObject> {
    int trackIndex = 0;
    private int trackNumDiff;
    private List<trackDifference> trackDiffList = new ArrayList<>();

    public Difference(int trackNumDiff) {
        this.trackNumDiff = trackNumDiff;
    }

    /**
     *
     * @param list1
     * @param list2
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

        trackDiffList.add(new trackDifference(trackIndex,NumDiff,
                new HashSet<>(OriOrbit1Objs),new HashSet<>(OriOrbit2Objs)));

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("轨道数差异：%d\n",trackNumDiff));
        for(trackDifference trackdiff:trackDiffList) {
            sb.append(trackdiff.toString()+"\n");
        }
        return sb.toString();
    }

    private class trackDifference {
        int index;
        int objNumDiff;
        Set<E> orbit1Objs,orbit2Objs;

        public trackDifference(int index,int objNumDiff,Set<E> orbit1Objs,Set<E> orbit2Objs) {
            this.index = index;
            this.objNumDiff = objNumDiff;
            this.orbit1Objs = orbit1Objs;
            this.orbit2Objs = orbit2Objs;
        }

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
