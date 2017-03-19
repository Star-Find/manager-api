package net.starfind.api.model;

public enum Role {
	FOUNDER(FriendChatRank.GENERAL, ClanRank.DEPUTY_OWNER),
	CUSTODIAN(FriendChatRank.GENERAL, ClanRank.DEPUTY_OWNER),
	ADMINISTRATOR(FriendChatRank.GENERAL, ClanRank.OVERSEER),
	ELDER(FriendChatRank.CAPTAIN, ClanRank.COORDINATOR),
	CAPTAIN(FriendChatRank.CAPTAIN, ClanRank.ORGANISER),
	RETIRED_RANK(FriendChatRank.LIEUTENANT, ClanRank.ADMIN),
	LIEUTENANT(FriendChatRank.LIEUTENANT, ClanRank.LIEUTENANT),
	SERGEANT(FriendChatRank.SERGEANT, ClanRank.SERGEANT),
	CORPORAL(FriendChatRank.CORPORAL, ClanRank.CORPORAL),
	INACTIVE_RIT(FriendChatRank.RECRUIT, ClanRank.RECRUIT),
	CONTRIBUTOR(FriendChatRank.FRIEND, null);
	
	Role(FriendChatRank fcRank, ClanRank clanRank) {
		
	}
}
