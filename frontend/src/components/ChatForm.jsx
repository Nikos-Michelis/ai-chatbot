import {Button} from "@/components/button/Button.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowUp} from "@fortawesome/free-solid-svg-icons";
import {useForm} from "react-hook-form";

const ChatForm = ({setChatHistory, handleAskAi}) => {
    const {
        register,
        handleSubmit,
        reset,
        formState: {errors, isValid }} = useForm({
        mode: 'onChange',
        defaultValues: {
            message: "",
        },
    });

    const rules = {
        required: true,
    }

    const onSubmit = (data) => {
        const message = data?.message?.trim();
        reset();
        setChatHistory(prev => [...prev, { role: "user", text: message }]);
        handleAskAi(message);
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="chat__form">
            <input
                className="chat__message-input"
                type="text"
                name="message"
                {...register("message",  rules)}
                placeholder="Ask anything..."
            />
            <Button
                className={`btn--transparent${isValid ? ' btn--visible' : ' btn--hide'} clr-star-400`}
                type="submit">
                <FontAwesomeIcon icon={faArrowUp} className="btn__icon"/>
            </Button>
        </form>
    )
}

export default ChatForm;