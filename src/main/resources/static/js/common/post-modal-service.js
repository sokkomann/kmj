// 공용 모달 전용 service — 가입 커뮤니티 조회 + 커뮤니티 게시 endpoint 호출 + 토스트
const postModalService = (() => {

    const getMyCommunities = async (page = 1) => {
        const res = await fetch(`/api/communities/my/${page}`);
        return await res.json();
    };

    const writeCommunityPost = async (communityId, formData) => {
        await fetch(`/api/communities/${communityId}/posts`, { method: "POST", body: formData });
    };

    const describeImage = async (base64Url) => {
        const response = await fetch('/ai/image-describe', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ imageBase64: base64Url })
        });
        const data = await response.json();
        return data.description;
    };

    const predictView = async (content, tags, descriptions, callback) => {
        const response = await fetch("/ai/predict-view", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                content: content,
                tags: tags,
                descriptions: descriptions
            })
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "조회수 예상 실패");
        }
        const result = await response.json();
        if (callback) {
            callback(result);
        }
        return result;
    };

    // 게시 후 자동 토스트 — main의 .notification-toast 클래스 재사용
    const showToast = (message) => {
        const existing = document.querySelector(".notification-toast");
        if (existing) existing.remove();
        const toast = document.createElement("div");
        toast.className = "notification-toast";
        toast.textContent = message;
        document.body.appendChild(toast);
        setTimeout(() => toast.remove(), 3000);
    };

    return {
        getMyCommunities: getMyCommunities,
        writeCommunityPost: writeCommunityPost,
        showToast: showToast,
        describeImage: describeImage,
        predictView: predictView
    };
})();
