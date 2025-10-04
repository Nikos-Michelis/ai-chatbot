import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAngleDown, faXmark} from "@fortawesome/free-solid-svg-icons";
import {Button} from "@/components/button/Button.jsx";
import { BotMessageSquare } from 'lucide-react';
import {faCommentDots} from "@fortawesome/free-solid-svg-icons/faCommentDots";
import ChatForm from "@/components/ChatForm.jsx";
import {useEffect, useRef, useState} from "react";
import ChatMessage from "@/components/ChatMessage.jsx";
import {useCreateMutation} from "@/services/mutations.jsx";
import ChatLoader from "@/components/ChatLoader.jsx";
import ChatError from "@/components/ChatError.jsx";

const AiChat = () => {
    const baseUrl = "http://localhost:8080/api/v1";
    const chatBodyRef = useRef();
    const [chatHistory, setChatHistory] = useState([]);
    const [isOpen, setIsOpen] = useState(false);
    const askAiMutation =
        useCreateMutation({
            successMessage: undefined,
            showError: false
        });

    const handleAskAi = (question) => {
        askAiMutation.mutate({
            url: baseUrl + "/ai/qna",
            data: { question: question },
            options: { withCredentials: false, Bearer: false },
        }, {
            onSuccess: (res) => {
                setChatHistory(prev =>
                    [...prev, { role: res?.candidates?.[0]?.content?.role, text: res?.candidates?.[0]?.content?.parts[0]?.text }]);
            },
            onError: (error) => {
                console.error(error);
            },
        })
    };

    useEffect(() => {
        chatBodyRef.current.scrollTo({top: chatBodyRef.current?.scrollHeight, behavior: "smooth"});
    }, [chatHistory]);

    return (
        <>
            <Button className="btn--chatbot" onClick={() => setIsOpen(prevState => !prevState)}>
                <FontAwesomeIcon icon={isOpen ? faXmark : faCommentDots}/>
            </Button>
            <div className={`chat__popup ${isOpen && "chat__show-popup"}`}>
                <div className="chat__header">
                    <div className="logo">
                        <FontAwesomeIcon icon={faCommentDots} className="logo__icon"/>
                        <h2 className="logo__text">Gemini Chatbot</h2>
                    </div>
                    <Button className="btn--transparent hover scale-small" onClick={() => setIsOpen(prevState => !prevState)}>
                        <FontAwesomeIcon icon={faAngleDown}/>
                    </Button>
                </div>

                <div ref={chatBodyRef} className="chat__body">
                    <div className="message message--bot">
                        <BotMessageSquare className="message__avatar" />
                        <p className="message__text">
                            Hey there 👋 <br/> How can I help you today?
                        </p>
                    </div>
                    {
                        chatHistory?.map((chat, index) => (
                            <ChatMessage key={index} chat={chat}/>
                        ))
                    }
                    {askAiMutation.isPending && <ChatLoader/>}
                    {askAiMutation.isError && <ChatError/>}
                </div>
                <div className="chat__footer">
                    <ChatForm setChatHistory={setChatHistory} handleAskAi={handleAskAi}/>
                </div>
            </div>
        </>
    );
}

export default AiChat;