import {BotMessageSquare} from "lucide-react";

const ChatMessage = ({chat}) => {
    return (
        <div className={`message ${ chat?.role !== "model" ? "message--user" : "message--bot"}`}>
            { (chat?.role === "model") && <BotMessageSquare className="message__avatar" /> }
            <p className="message__text">{chat?.text}</p>
        </div>
    )
}

export default ChatMessage;