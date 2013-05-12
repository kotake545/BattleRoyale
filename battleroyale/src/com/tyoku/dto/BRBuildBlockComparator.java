package com.tyoku.dto;

import java.util.Comparator;

public class BRBuildBlockComparator implements Comparator<BRBuildBlock> {

	@Override
	public int compare(BRBuildBlock o1, BRBuildBlock o2) {
		int ret = 0;
		ret = o1.getType().compareTo(o2.getType());
		if(ret == 0){
			ret = o1.getX()+o1.getZ() - o2.getX()+o2.getZ();
		}
		if(ret == 0){
			ret = o1.getY() - o2.getY();
		}
		return ret;
	}

}
