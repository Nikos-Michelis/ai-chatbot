import {BotMessageSquare} from "lucide-react";

const ChatLoader = () => {
    return(
        <div className="message message--bot">
            <BotMessageSquare className="message__avatar" />
            <div className="message__text"><div className="loader"></div></div>
        </div>
    );
}

export default ChatLoader;