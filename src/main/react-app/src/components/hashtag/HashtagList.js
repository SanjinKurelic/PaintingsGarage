import PropTypes from 'prop-types'
import {WithContext as ReactTag} from 'react-tag-input'
import './hashtagList.scss'

// Define styles
const styleClasses = {
  tag: 'hashtag-tag'
}

const HashtagList = ({hashtagItems, readOnly}) => {
  // Format data
  const formatTags = () => {
    if (readOnly === false) {
      return hashtagItems;
    }

    return hashtagItems.map((tag, index) => {
      return {
        id: ('' + index),
        text: ('#' + tag)
      }
    })
  }
  const tags = formatTags();

  const tagClick = (index) => {
    console.log(tags[index]); // TODO search by tag
  }

  return (
    <ReactTag classNames={styleClasses} tags={tags} readOnly={readOnly} handleTagClick={tagClick}/>
  )
}

HashtagList.propTypes = {
  hashtagItems: PropTypes.array,
  readOnly: PropTypes.bool
}

HashtagList.defaultProps = {
  hashtagItems: [],
  readOnly: true
}

export default HashtagList