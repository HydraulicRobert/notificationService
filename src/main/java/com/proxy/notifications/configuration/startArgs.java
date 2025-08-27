package com.proxy.notifications.configuration;

import java.util.ArrayList;
import java.util.List;

public class startArgs {
	private List<String[]> stlArgsList;
	public startArgs()
	{
		stlArgsList = new ArrayList<String[]>();
		stlArgsList.add(global.getGstrnotadduser());
	    stlArgsList.add(global.getGstrnotremoveuser());
	    stlArgsList.add(global.getGstrnotshowuser());
	    stlArgsList.add(global.getGstrnotconfigure());
	    stlArgsList.add(global.getGstrnothelp());
	}
	
	public void pickStartArgs(String[] args)
	{
		
			//add
			if (args[0].trim().equals(stlArgsList.get(0)[0]) ||
				args[0].trim().equals(stlArgsList.get(0)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(0)[3])))
				{
					addUser(args);
				}else
				{
					System.out.println(global.getGstrnotargumentsamount()+stlArgsList.get(0)[3]);
				}
			}else 
			//remove
			if (args[0].trim().equals(stlArgsList.get(1)[0]) ||
					args[0].trim().equals(stlArgsList.get(1)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(1)[3])))
				{
					removeUser(args);
				}else
				{
					System.out.println(global.getGstrnotargumentsamount()+stlArgsList.get(1)[3]);
				}
			}else 
			//show
			if (args[0].trim().equals(stlArgsList.get(2)[0]) ||
				args[0].trim().equals(stlArgsList.get(2)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(2)[3])))
				{
					showUsers(args);
				}else
				{
					System.out.println(global.getGstrnotargumentsamount()+stlArgsList.get(2)[3]);
				}
			}else
				
			//help
			if (args[0].trim().equals(stlArgsList.get(4)[0]) ||
					args[0].trim().equals(stlArgsList.get(4)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(4)[3])))
				{
					help(args, stlArgsList);
				}else
				{
					System.out.println(global.getGstrnotargumentsamount()+stlArgsList.get(4)[3]);
				}
			}
	}
	
	private boolean addUser(String[] args)
	{
		boolean userCreated = false;
		String strCfgPath = global.getGstrcfgpath();
		String strFileName = global.getGstruserlist();
		if (!cfgInputOutput.createFile(strCfgPath, strFileName))
		{
			System.out.println(global.getGstrnotfldduserfilecreate());
		}else {
			System.out.println(global.getGstrnotuserfilecreate());
		}
		cfgInputOutput.addUserFile(args[1],args[2],strCfgPath,strFileName);
		userCreated = true;
		return userCreated;
	}
	private boolean removeUser(String[] args)
	{
		if(cfgInputOutput.removeUserFile(args[1], global.getGstrcfgpath(), global.getGstruserlist()))
		{
			return true;
		}else
		{
			return false;
		}
		
	}
	private void showUsers(String[] args)
	{
		List<String[]> userList = cfgInputOutput.getUserList(global.getGstrcfgpath(), global.getGstruserlist());
		for (int i = 0; i<userList.size();i++) {
			System.out.println(global.getGstrnotshowusersname()+userList.get(i)[0]+global.getGstrnotshowuserspassword()+userList.get(i)[1]+"';");
		}
	}
	private void help(String[] args, List<String[]> stlArgsList)
	{
		for (String[] strArrForLoop : stlArgsList) {
			for (String strForLoop : strArrForLoop) {
				System.out.print(
						strForLoop+"; "
						);
			}
			System.out.println();
		}
	}
	public List<String[]> getStlArgsList() {
		return stlArgsList;
	}
}
