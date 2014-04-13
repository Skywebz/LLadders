package se.luppii.ladders.updater;

public class ModVersion implements Comparable<ModVersion> {
	
	private int _major;
	private int _minor;
	private int _patch;
	private String _desc;
	
	public ModVersion(int major, int minor, int patch, String description) {
		
		_major = major;
		_minor = minor;
		_patch = patch;
		_desc = description;
	}
	
	public int major() {
		
		return _major;
	}
	
	public int minor() {
		
		return _minor;
	}
	
	public int patch() {
		
		return _patch;
	}
	
	public String description() {
		
		return _desc;
	}
	
	public static ModVersion parse(String par1) {
		
		String[] parts = par1.split(":", 2);
		String desc = "";
		if (parts.length > 1) {
			desc = parts[1];
		}
		
		parts = parts[0].split("\\.");
		int[] i = new int[parts.length];
		for (int j = 0; j < i.length; j++) {
			
			i[j] = Integer.parseInt(parts[j]);
		}
		
		return new ModVersion(i[0], i[1], i[2], desc);
	}
	
	@Override
	public int compareTo(ModVersion par1) {
		
		if (this.major() != par1.major()) return this.major() < par1.major() ? -1 : 1;
		if (this.minor() != par1.minor()) return this.minor() < par1.minor() ? -1 : 1;
		if (this.patch() != par1.patch()) return this.patch() < par1.patch() ? -1 : 1;
		
		return 0;
	}
	
	@Override
	public String toString() {
		
		return _major + "." + _minor + "." + _patch;
	}
}
