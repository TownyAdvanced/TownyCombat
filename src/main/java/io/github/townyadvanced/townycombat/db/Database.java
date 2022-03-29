package io.github.townyadvanced.townycombat.db;

import java.io.File;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.palmergames.util.FileMgmt;

import io.github.townyadvanced.townycombat.TownyCombat;


public class Database {
	
	final static String rootFolderPath = TownyCombat.getPlugin().getDataFolder().getPath();
	protected final static Queue<Runnable> queryQueue = new ConcurrentLinkedQueue<>();
	private static BukkitTask task; 
	
	public Database(TownyCombat plugin) {
		if (!FileMgmt.checkOrCreateFolders(
			rootFolderPath,
			rootFolderPath + File.separator + "ruins",
			rootFolderPath + File.separator + "ruins" + File.separator + "deleted"
		))
			TownyCombat.severe("Could not create default folders");
		
		task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
			while (!Database.queryQueue.isEmpty()) {
				Runnable operation = Database.queryQueue.poll();
				operation.run();
			}
		}, 5L, 5L);
	}
	
	public static void finishTasks() {
		// Cancel the repeating task as its not needed anymore.
		task.cancel();
		
		// Make sure that *all* tasks are saved before shutting down.
		while (!queryQueue.isEmpty()) {
			Runnable operation = Database.queryQueue.poll();
			operation.run();
		}
	}
	
	public enum FileType {
		RUIN("ruins", ".txt");
		
		private String foldername;
		private String fileExtension;
		
		FileType(String foldername, String fileExtension) {
			this.foldername = foldername;
			this.fileExtension = fileExtension;
		}
	}

	public static String getFileName(FileType type, UUID uuid ) {
		return rootFolderPath + File.separator + type.foldername + File.separator + uuid + type.fileExtension;
	}
	
	public static boolean loadFileList(FileType type, Consumer<UUID> consumer) {
		TownyCombat.info("Searching for " + type.foldername + "...");
		File[] files = new File(rootFolderPath + File.separator + type.foldername)
							       .listFiles(file -> file.getName().toLowerCase().endsWith(type.fileExtension));
		
		if (files.length == 0) {
			TownyCombat.info("No " + type.foldername + " found.");
			return true;
		}
		
		TownyCombat.info("Loading " + type.foldername + " list...");

		for (File file : files)
			consumer.accept(UUID.fromString(file.getName().replace(type.fileExtension, "")));
			
		return true;
	}

//	public static boolean loadRuin(Ruin ruin) {
//		String line = null;
//		String[] tokens;
//		File fileRuin = new File(getFileName(FileType.RUIN, ruin.getTownUUID()));
//		if (fileRuin.exists() && fileRuin.isFile()) {
//		    
//			try {
//				HashMap<String, String> keys = com.palmergames.util.FileMgmt.loadFileIntoHashMap(fileRuin);
//				line = keys.get("name");
//				if (line != null)
//				    ruin.setName(line);
//			} catch (Exception e) {
//				BlankPlugin.severe("Ruin: " + ruin.getName() + " could not be loaded at line " + line + ", deleting this ruin.");
//				e.printStackTrace();
//				return false;
//			}
//		}
//		return true;
//	}
//	
//	public static void saveRuin(Ruin ruin) {
//		List<String> list = new ArrayList<>();
//		list.add("name=" + ruin.getName());
//		list.add("foundedDate=" + ruin.getFoundedDate());
//		list.add("fallenDate=" + ruin.getFallenDate());
//		list.add("mayor=" + ruin.getMayor());
//		list.add("size=" + ruin.getSize());
//		list.add("reasonFallen=" + ruin.getReasonFallen());
//		list.add("nationName=" + (ruin.getNationName() != null ? ruin.getNationName() : ""));
//		list.add("nationUUID=" + (ruin.getNationUUID() != null ? ruin.getNationUUID().toString() : ""));
//		list.add("spawnPoint=" + ruin.getSpawnPoint().toString());
//		list.add("residents=" + StringMgmt.join(ruin.getResidents(), ","));
//		list.add("coords=" + StringMgmt.join(ruin.getCoords(), ":"));
//		
//		queryQueue.add(new FlatFileSaveTask(list, getFileName(FileType.RUIN, ruin.getTownUUID())));
//	}
//	
//	public static void deleteRuin(Ruin ruin) {
//		File file = new File(getFileName(FileType.RUIN, ruin.getTownUUID()));
//		queryQueue.add(new DeleteFileTask(file, false));
//	}
}

