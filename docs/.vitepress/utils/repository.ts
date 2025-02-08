import axios from 'axios';

export async function getLatestReleaseOrTag(repository: string): Promise<string> {
    const releaseUrl = `https://api.github.com/repos/${repository}/releases/latest`;
    const tagsUrl = `https://api.github.com/repos/${repository}/tags`;

    try {
        const releaseResponse = await axios.get(releaseUrl);
        return releaseResponse.data.tag_name;
    } catch (releaseError) {
        console.error('Error fetching the latest release, falling back to tags.');
        try {
            const tagsResponse = await axios.get(tagsUrl);
            if (tagsResponse.data.length > 0) {
                return tagsResponse.data[0].name;
            } else {
                return 'No tags found';
            }
        } catch (tagsError) {
            console.error('Error fetching the tags:', tagsError);
            return 'unknown';
        }
    }
}