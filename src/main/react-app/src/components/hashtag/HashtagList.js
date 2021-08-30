import PropTypes from 'prop-types'
import Hashtag from './Hashtag'

const HashtagList = ({hashtagItems}) => {
  return (
    <>
      {hashtagItems.map((hashtag, index) => (
        <Hashtag key={index} value={hashtag}/>
      ))}
    </>
  )
}

HashtagList.propTypes = {
  hashtagItems: PropTypes.array
}

HashtagList.defaultProps = {
  hashtagItems: []
}

export default HashtagList