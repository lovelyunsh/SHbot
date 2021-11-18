package SHBot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class botMain extends ListenerAdapter {
	static JDA jda;
	public static void main(String[] args) throws LoginException {
		jda = JDABuilder.createDefault("OTEwNDA2MjA1Nzk5ODA5MDU0.YZSX2Q.jVSqlP7L27nWRdWLcXzxqeu2t6w").build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.addEventListener(new botMain());
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] message = event.getMessage().getContentRaw().split(" ");

		//bot이 보낸 메세지 무시
		if(event.getAuthor().isBot()) 
			return;
		//메세지 출력
		if (event.isFromType(ChannelType.PRIVATE)) {
			System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
		} else {
			System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(),
					event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());
		}
		
		if(message[0].equals("!사다리")) {
			List<Member> memberList = new ArrayList<Member>();
			//메세지 보낸 사람이 포함된 voice channel의 멤버들 가져오기
			gg:for(VoiceChannel v :jda.getVoiceChannels()) {
				for(Member m: v.getMembers()) {
					if(m.getIdLong() == event.getAuthor().getIdLong()) {
						memberList.addAll(v.getMembers());
						break gg;
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			if(memberList.size() == 0)
				sb.append("음성채널에 들어가 주세요.");
			else {
				Collections.shuffle(memberList);
				int idx = 1;
				for(Member m : memberList) {
					sb.append(idx++).append(" : ").append(m.getUser().getName()).append("\n");
				}
			}
			event.getChannel().sendMessage(sb.toString()).queue();
		}
	}

}
