const aiService = (() => {
    const conversation = async (question,callback) => {
        const response = await fetch("/ai/feed-chat", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({ question: question})
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "답변 실패");
        }
        const result = await response.json();
        if (callback) {
            callback(result);
        }
        console.log("ai서비스들어옴")
        return result;
    };

    return {conversation: conversation};
})();