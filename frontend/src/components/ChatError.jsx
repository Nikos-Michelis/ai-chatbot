import {BotMessageSquare} from "lucide-react";

const ChatError = () => {
    return (
        <div className="message message--bot">
            <BotMessageSquare className="message__avatar" />
            <div className="message__text message__text--error">Oops! somthing went wrong try again later.</div>
        </div>
    );
}

export default ChatError