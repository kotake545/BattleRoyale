package com.tyoku.dto;

import java.io.Serializable;
import java.util.List;

import org.bukkit.Material;

public class BRBuildBlock implements Serializable{
	private static final long serialVersionUID = 199035831519635924L;

	private int x;
	private int y;
	private int z;
	private Material type;
	private byte blockData;
	private boolean isEmpty;
	private byte[] byteOfMaterial;
	private List<Material> materialTypes;

	public BRBuildBlock() {
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Byte getBlockData() {
		return blockData;
	}

	public void setBlockData(Byte blockData) {
		this.blockData = blockData;
	}

	public Material getType() {
		return type;
	}

	public void setType(Material type) {
		this.type = type;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public byte[] getByteOfMaterial() {
		return byteOfMaterial;
	}

	public void setByteOfMaterial(byte[] byteOfMaterial) {
		this.byteOfMaterial = byteOfMaterial;
	}

	public List<Material> getMaterialTypes() {
		return materialTypes;
	}

	public void setMaterialTypes(List<Material> materialTypes) {
		this.materialTypes = materialTypes;
	}
}
